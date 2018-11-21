Este documento explica o que foi feito durante a Parte 1 do teste. Basicamente a análise exploratória foi dividida em três fases. Na primeira é realizada a leitura e o carregamento dos dados contidos nos arquivos csv do MovieLens. Nesta etapa, apenas os arquivos movies e ratings foram lidos.

Na segunda fase, uma análise estatística básica é feita com os dados. Informações de total de filmes, total de usuários, total de ratings, número médio de ratings por filme e rating médio foram obtidas dos arquivos.

Por fim, algumas características gerais sobre os dados são extraídas na terceira fase. Informações de qual foi o filme mais visto (mais ratings), filme com maior rating (considerando apenas filmes com número de ratings maior que número médio de ratings por filme), gênero mais visto (mais ratings), gênero com maior rating (relação entre gênero e rating), gênero com mais filmes, gênero com mais filmes de rating 5 (mais filmes "bons"), gênero com mais filmes de rating 5 (mais filmes "bons") proporcional ao que produziu, gênero com maior desvio padrão no rating (maior heterogeneidade entre os usuários) e gêneros mais correlacionados (filmes que possuem ambos os gêneros) foram obtidas dos arquivos.

A análise exploratória foi realizada com a linguagem de programação Java, junto das APIs Apache Commons CSV e Apache Commons Math.
