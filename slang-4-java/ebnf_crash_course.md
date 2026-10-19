# EBNF Crash Course

## 1. What EBNF is

EBNF (Extended Backus-Naur Form) is a notation for describing the syntax of a language. Think of it as a recipe for what programs are allowed to look like. For example, "a variable declaration is a type, then a name, then a semicolon" becomes:

```
<vardecl> ::= <type> <variable> ";"
```

This permits `NUMERIC x;`, `STRING name;`, and `BOOLEAN flag;`.

## 2. The building blocks

**Rules.** A grammar is a list of rules of the form `<name> ::= definition`. Read `::=` as "is defined as."

**Non-terminals** are names in `< >`, like `<expr>` or `<stmt>`. They are placeholders that must be expanded further by another rule.

**Terminals** are the literal symbols and keywords that appear in a program, such as `IF`, `THEN`, `ENDIF`, `+`, `;`, and `(`. In the rule `<ifstmt> ::= IF <expr> THEN <stmts> ENDIF`, `IF`, `THEN`, and `ENDIF` are terminals, while `<expr>` and `<stmts>` are non-terminals.

## 3. The operators

**Alternation (`|`)** means "or":

```
<type> ::= NUMERIC | STRING | BOOLEAN
<ADD_OP> ::= "+" | "-"
```

**Sequence** (items written side by side) means "followed by." In `<type> <variable> ";"`, the items must appear in that order.

**Grouping (`( )`)** lets you pick from alternatives inline:

```
<Factor> ::= <number>
           | <variable>
           | "(" <expr> ")"
           | ("+" | "-" | "!") <Factor>
```

This allows `5`, `x`, `(x + 5)`, `-x`, `+x`, and `!x`.

**The three repetition patterns to memorize:**

| Notation | Meaning | Example |
|---|---|---|
| `[ X ]` | optional (0 or 1) | `[ ELSE <stmts> ]` |
| `{ X }` | zero or more | `<program> ::= { <stmt> }` |
| `X+` | one or more | `<stmts> ::= <stmt>+` |

`X?` is a dialect that means the same as `[ X ]`. Notation varies between textbooks and tools, so check what your course expects. Unless told otherwise, stick with `[ ]` for optional.

The difference between `{ <stmt> }` and `<stmt>+` is that the first allows an empty program and the second does not.

**A very common pattern** is a comma-separated list:

```
<list> ::= <number> { "," <number> }
```

This accepts `1`, `1, 2`, and `1, 2, 3`, but not `1,` because every comma must be followed by a number.

## 4. Applying it to your grammar

**Statements.** `<stmt>` can be any one of six kinds (variable declaration, print, assignment, if, while, println), and `<stmts> ::= <stmt>+` means one or more of them in a row. So this program is a sequence of four statements:

```
NUMERIC x;     → <vardeclstmt>
PRINT x;       → <printstmt>
x = 10;        → <assignmentstmt>
PRINT x;       → <printstmt>
```

**The IF statement:**

```
<ifstmt> ::= IF <expr> THEN <stmts> [ ELSE <stmts> ] ENDIF
```

Both of these match, without and with the ELSE:

```
IF x > 10 THEN
    PRINT x;
ENDIF
```

```
IF x > 10 THEN
    PRINT x;
ELSE
    PRINT 0;
ENDIF
```

**Expressions and precedence.** Your expression rules are layered so that each level calls the one below it:

```
<BExpr>  → logical operators          (lowest precedence)
<LExpr>  → relational operators
<RExpr>  → addition / subtraction
<Term>   → multiplication / division
<Factor> → individual values          (highest precedence)
```

Operators lower in the chain bind more tightly, which is why `x + y * 5` is parsed as `x + (y * 5)` and not `(x + y) * 5`.

One caution: as written, a rule like `<BExpr> ::= <LExpr> LOGIC_OP <BExpr>` always requires an operator, so a plain `x` could never match. The usual fix is to make the operator part optional:

```
<BExpr> ::= <LExpr> [ LOGIC_OP <BExpr> ]
```

The same applies to `<LExpr>`, `<RExpr>`, and `<Term>`. Alternatively, you can use repetition: `<RExpr> ::= <Term> { ADD_OP <Term> }`.

## 5. EBNF vs. BNF

BNF lacks the optional and repetition shorthand, so it needs recursion and duplicated rules:

```
<stmts>  ::= <stmt> | <stmt> <stmts>
<ifstmt> ::= IF <expr> THEN <stmts> ENDIF
           | IF <expr> THEN <stmts> ELSE <stmts> ENDIF
```

EBNF expresses the same thing more compactly:

```
<stmts>  ::= <stmt>+
<ifstmt> ::= IF <expr> THEN <stmts> [ ELSE <stmts> ] ENDIF
```

That is the whole reason EBNF exists: grammars that are easier to write and read.

## 6. Syntax is not meaning

EBNF only describes structure. The rule `<assignment> ::= <variable> "=" <expr> ";"` accepts `x = 10;` even if `x` was never declared. Rules like "variables must be declared before use" are semantic, and are enforced by later stages of the compiler or interpreter.

> EBNF answers "Does this have the right structure?" not "Does this make sense?"

## 7. Cheat sheet

| EBNF | Meaning | Example |
|---|---|---|
| `<X>` | Non-terminal | `<expr>` |
| `"x"` | Terminal | `"+"` |
| `::=` | Is defined as | `<expr> ::= ...` |
| `\|` | Or | `A \| B` |
| `[ X ]` | Optional (0 or 1) | `[ ELSE <stmts> ]` |
| `{ X }` | Zero or more | `{ "," <number> }` |
| `X+` | One or more | `<stmt>+` |
| `( X )` | Grouping | `("+" \| "-")` |
| `A B` | Sequence | `<type> <variable>` |

**Mental model:** read `<A> ::= B [ C ] { D } | E` as "A is either B, optionally followed by C, followed by zero or more D's, or else just E."

Once you're comfortable with sequence, alternation, optional, repetition, and grouping, you know roughly 90% of the EBNF a typical compiler or parser assignment needs.
