# Rules-Engine

Next generation rules engine - `Rules-Engine` is written in TypeScript.

## Working with the repo

### Setup

Requires:

-   [Node 16](https://nodejs.org/download/release/v16.14.2/)

### Relevant commands

Install the dependencies

```bash
$ npm ci
```

Run the tests

```bash
$ npm run test
```

Fix lint issues

```bash
$ npm run lint-fix
```

Build the package

```bash
$ npm run build
```

## Using the implementation

Follow the steps mentioned in the [parent README](https://github.com/WarnerMedia/Rules-Engine#using-the-implementations)
first.

Add the following to `.npmrc`:

```
@warnermedia:registry=https://npm.pkg.github.com
//npm.pkg.github.com/:_authToken=${GITHUB_TOKEN}
```

Initial Contributions from:

-   [Satvik Shukla](https://github.com/satvik-s)
-   [Samed Ozdemir](https://github.com/xsor-hbo)
