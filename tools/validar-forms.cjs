#!/usr/bin/env node
/**
 * Valida os ficheiros .form contra o esquema oficial da Camunda.
 *
 * O linter de BPMN garante que a tarefa de utilizador declara um formId; nao
 * garante que o formulario correspondente exista nem que seja valido. Esta
 * ferramenta fecha essa lacuna, e verifica tambem a correspondencia nos dois
 * sentidos entre os formId declarados no BPMN e os ficheiros em disco.
 *
 * Uso:  node tools/validar-forms.cjs [directorio-forms] [directorio-bpmn]
 */
const fs = require('fs');
const path = require('path');
const Ajv = require('ajv');
const addErrors = require('ajv-errors');
const schema = require('@bpmn-io/form-json-schema/resources/schema.json');

const DIR_FORMS = process.argv[2] || 'bpmn/forms';
const DIR_BPMN = process.argv[3] || 'bpmn/to-be';

const ajv = new Ajv({ allErrors: true, strict: false });
addErrors(ajv);
const validate = ajv.compile(schema);

let falhas = 0;
const idsEmDisco = new Set();

const ficheiros = fs.existsSync(DIR_FORMS)
  ? fs.readdirSync(DIR_FORMS).filter((f) => f.endsWith('.form')).sort()
  : [];

if (!ficheiros.length) {
  console.error(`Nenhum .form encontrado em ${DIR_FORMS}`);
  process.exit(1);
}

for (const f of ficheiros) {
  const caminho = path.join(DIR_FORMS, f);
  let form;
  try {
    form = JSON.parse(fs.readFileSync(caminho, 'utf8'));
  } catch (e) {
    console.error(`  ERRO  ${f}: JSON invalido — ${e.message}`);
    falhas++;
    continue;
  }
  if (!validate(form)) {
    console.error(`  ERRO  ${f}:`);
    console.error(JSON.stringify(validate.errors, null, 2));
    falhas++;
    continue;
  }
  // O id do formulario tem de coincidir com o nome do ficheiro: e por ele que
  // o BPMN o referencia, e uma divergencia so aparece em execucao.
  const esperado = path.basename(f, '.form');
  if (form.id !== esperado) {
    console.error(`  ERRO  ${f}: id "${form.id}" difere do nome do ficheiro "${esperado}"`);
    falhas++;
    continue;
  }
  idsEmDisco.add(form.id);
  const n = (form.components || []).length;
  console.log(`  ok    ${f}  (${n} componente${n === 1 ? '' : 's'})`);
}

// Correspondencia nos dois sentidos com os formId declarados no BPMN.
const idsNoBpmn = new Set();
if (fs.existsSync(DIR_BPMN)) {
  for (const f of fs.readdirSync(DIR_BPMN).filter((x) => x.endsWith('.bpmn'))) {
    const xml = fs.readFileSync(path.join(DIR_BPMN, f), 'utf8');
    for (const m of xml.matchAll(/formId="([^"]+)"/g)) idsNoBpmn.add(m[1]);
  }
}

for (const id of idsNoBpmn) {
  if (!idsEmDisco.has(id)) {
    console.error(`  ERRO  BPMN declara formId "${id}" e nao existe ${id}.form`);
    falhas++;
  }
}
for (const id of idsEmDisco) {
  if (idsNoBpmn.size && !idsNoBpmn.has(id)) {
    console.error(`  AVISO ${id}.form existe e nenhum BPMN o referencia`);
    falhas++;
  }
}

console.log(
  `\n${ficheiros.length} formularios, ${idsNoBpmn.size} formId declarados no BPMN, ${falhas} problema(s).`
);
process.exit(falhas ? 1 : 0);
