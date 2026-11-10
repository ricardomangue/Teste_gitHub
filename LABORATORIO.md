# Laboratório: Revisão de código com IA (CodeRabbit + GitHub)

Projecto base: **student-crud-laboratorio** (Java 17, Spring Boot 3, Thymeleaf, JPA, H2).

## 0. Preparação da `main` (uma vez, pelo responsável)

Se o repositório já tem `target/`, `.idea/` ou `.DS_Store` versionados, removê-los do Git:

```bash
git rm -r --cached target .idea src/.DS_Store .DS_Store 2>/dev/null
git add .gitignore .coderabbit.yaml README.md LABORATORIO.md etapas pom.xml src
git commit -m "Preparar base do laboratório de revisão com IA"
git push origin main
```

A `main` fica **limpa**. As falhas propositadas só entram nas branches de cada etapa.
Instalar a GitHub App do CodeRabbit antes de abrir o primeiro Pull Request.

## 1. Como usar cada etapa

Para cada etapa: criar a branch, copiar os ficheiros da pasta `etapas/NN-...` para a raiz do projecto (mantêm os caminhos), fazer commit, push e abrir o PR com o texto de `PR-descricao.md`.

```bash
git checkout main && git pull
git checkout -b 2-boas-praticas
cp -r etapas/02-boas-praticas/src ./        # copia apenas a pasta src
git add src
git commit -m "Adicionar StudentService para revisão de boas práticas"
git push -u origin 2-boas-praticas
```

(Na Etapa 3 usar `3-otimizacao` e na Etapa 4 `4-auditoria-seguranca`, com as pastas correspondentes.)
Não copiar a pasta `etapas/` para dentro do PR; o ficheiro `PR-descricao.md` serve apenas de texto para a descrição.

## 2. Achados esperados (referência do docente)

### Etapa 2: boas práticas (`StudentService.java`)
| Achado | Onde |
|---|---|
| Injecção por campo (`@Autowired`) e atributo com visibilidade de pacote | `r` |
| Lista pública mutável como estado do serviço (não thread-safe) | `lista` |
| Nomes pouco descritivos (`proc`, `a`..`e`, `r`, `x`, `y`) | `proc` |
| Aninhamento profundo e método longo | `proc` |
| Números mágicos (13, 20) e retorno de `String` ("ok"/"erro"/`null`) | `proc` |
| `catch (Exception)` com `printStackTrace` e retorno `null` | `proc` |
| `System.out.println` em vez de logger | `proc` |
| Código duplicado e divisão por zero com lista vazia | `media1`, `media2` |
| Consulta repetida (`findAll` duas vezes) | `media1`, `media2` |
| Ausência de testes | projecto |

### Etapa 3: optimização e refactoração (`StudentStatsService.java`)
| Achado | Melhoria esperada |
|---|---|
| Bubble sort sobre `findAll()` (O(n²)) e `subList` sem validar `n` | Consulta ordenada/paginada (`Pageable`, `findTop...`) |
| Duplicados com ciclos aninhados e `contains` | Agrupamento com `Map`/`Set` ou query `GROUP BY` |
| `existe` carrega todos os registos | `repository.existsById(rollNo)` |
| `mediaCgpa` chama `findAll()` dentro do ciclo | Query agregada (`AVG`) ou um único `findAll` |
| Concatenação de `String` num ciclo | `StringBuilder` |
| Divisão por zero quando não há estudantes | Tratar caso vazio |

### Etapa 4: auditoria de segurança (`AdminController.java`, `mensagem.html`, `application.properties`)
| Achado | Categoria | Correcção esperada |
|---|---|---|
| Concatenação na query nativa | Injecção SQL | Parâmetros nomeados ou método do repositório |
| Palavra-passe e chave no código | Segredos expostos | Variáveis de ambiente / gestor de segredos |
| `MD5` para palavras-passe | Criptografia fraca | BCrypt/Argon2 (Spring Security Crypto) |
| `th:utext` com entrada do utilizador | XSS | `th:text` |
| `/admin/apagar-tudo` sem autenticação real | Controlo de acesso | Spring Security com papéis |
| Palavra-passe e chave escritas em logs | Dados sensíveis em logs | Não registar segredos; usar SLF4J |
| Palavra-passe enviada por GET | Dados sensíveis em URL | POST com corpo |
| `web-allow-others=true` e password no `.properties` | Configuração insegura | Desactivar consola/limitar acesso; segredos fora do ficheiro |

### Problemas reais já presentes na `main` (bónus: a IA pode assinalá-los)
- Entidade ligada directamente ao formulário (`@ModelAttribute Student`): sem Bean Validation e risco de *mass assignment*.
- `save` substitui silenciosamente um estudante com o mesmo `rollNo`.
- Respostas a `POST` devolvem a página em vez de redireccionar (padrão Post/Redirect/Get).
- `Double` para CGPA e `String` como chave primária; telefone sem validação.
- Consola H2 activa e `show-sql=true` por defeito.
- Spring Boot 3.1.0 está desactualizado; actualizar para uma versão suportada.
- Sem testes.

## 3. Aviso

Os ficheiros em `etapas/` são código de treino com falhas **propositadas** e credenciais **fictícias**. Não fazer deploy.
