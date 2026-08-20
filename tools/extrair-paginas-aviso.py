#!/usr/bin/env python3
"""
Extrai as paginas do Aviso n.o 1/23 do BNA a partir do PDF fonte.

O PDF publicado pelo Diario da Republica e um documento digitalizado: cada
pagina e uma imagem, sem camada de texto pesquisavel. Nao existe forma de o
processar automaticamente sem primeiro extrair as paginas.

As imagens resultantes NAO sao versionadas — sao derivaveis deste script e do
PDF fonte, que esta no repositorio. Correr sempre que for preciso consultar a
fonte visualmente.

Uso:
    python3 tools/extrair-paginas-aviso.py [PDF] [DIRECTORIO_SAIDA]

Predefinicoes:
    PDF               Safari.pdf
    DIRECTORIO_SAIDA  docs/_assets/aviso-01-23

Nao requer dependencias externas: descomprime os fluxos Flate e escreve PNG
com a biblioteca padrao; os fluxos DCT (JPEG) sao gravados tal como estao.
"""

import re
import struct
import sys
import zlib
from pathlib import Path

PDF_PREDEFINIDO = "Safari.pdf"
SAIDA_PREDEFINIDA = "docs/_assets/aviso-01-23"


def escrever_png(caminho: Path, largura: int, altura: int, cinza: bytes) -> None:
    """Escreve um PNG de 8 bits em tons de cinza a partir de amostras cruas."""

    def bloco(tipo: bytes, dados: bytes) -> bytes:
        corpo = tipo + dados
        return struct.pack(">I", len(dados)) + corpo + struct.pack(
            ">I", zlib.crc32(corpo) & 0xFFFFFFFF
        )

    # IHDR: largura, altura, profundidade 8, tipo de cor 0 (cinza), sem entrelacado
    ihdr = struct.pack(">IIBBBBB", largura, altura, 8, 0, 0, 0, 0)

    linhas = bytearray()
    for y in range(altura):
        linhas.append(0)  # byte de filtro: nenhum
        linhas += cinza[y * largura : (y + 1) * largura]

    caminho.write_bytes(
        b"\x89PNG\r\n\x1a\n"
        + bloco(b"IHDR", ihdr)
        + bloco(b"IDAT", zlib.compress(bytes(linhas), 6))
        + bloco(b"IEND", b"")
    )


def extrair(pdf: Path, saida: Path) -> int:
    dados = pdf.read_bytes()
    saida.mkdir(parents=True, exist_ok=True)

    escritas = 0
    for indice, marca in enumerate(re.finditer(rb"/Subtype\s*/Image", dados), start=1):
        inicio_dic = dados.rfind(b"obj", 0, marca.start())
        inicio_fluxo = dados.find(b"stream", marca.end())
        cabecalho = dados[inicio_dic:inicio_fluxo].decode("latin-1")

        def campo(nome: str) -> int:
            achado = re.search(rf"/{nome}\s+(\d+)", cabecalho)
            if not achado:
                raise ValueError(f"campo /{nome} ausente na imagem {indice}")
            return int(achado.group(1))

        largura, altura, comprimento = campo("Width"), campo("Height"), campo("Length")
        cru = dados[dados.find(b"\n", inicio_fluxo) + 1 :][:comprimento]

        if "/DCTDecode" in cabecalho:
            if cru[:2] != b"\xff\xd8":
                raise ValueError(f"imagem {indice}: fluxo DCT sem marca JPEG")
            destino = saida / f"p{indice:02d}.jpg"
            destino.write_bytes(cru)
        else:
            cinza = zlib.decompress(cru)
            if len(cinza) != largura * altura:
                raise ValueError(
                    f"imagem {indice}: {len(cinza)} amostras, "
                    f"esperadas {largura * altura}"
                )
            destino = saida / f"p{indice:02d}.png"
            escrever_png(destino, largura, altura, cinza)

        print(f"  p{indice:02d}  {largura}x{altura}  {destino.name}")
        escritas += 1

    return escritas


def main() -> int:
    pdf = Path(sys.argv[1] if len(sys.argv) > 1 else PDF_PREDEFINIDO)
    saida = Path(sys.argv[2] if len(sys.argv) > 2 else SAIDA_PREDEFINIDA)

    if not pdf.is_file():
        print(f"erro: PDF fonte nao encontrado: {pdf}", file=sys.stderr)
        return 1

    print(f"A extrair paginas de {pdf} para {saida}/")
    total = extrair(pdf, saida)
    print(f"{total} paginas extraidas.")
    return 0 if total else 1


if __name__ == "__main__":
    raise SystemExit(main())
