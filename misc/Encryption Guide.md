# Encryption Guide

`./sqlcipher plain.db3`

`ATTACH DATABASE 'encrypted.db3' AS encrypted KEY 'Dinis Cottage';`

`SELECT sqlcipher_export('encrypted'); `

`DETACH DATABASE encrypted;`

