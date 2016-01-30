# Encryption Guide

`./sqlcipher plain.db3`

`ATTACH DATABASE 'encrypted.db3' AS encrypted KEY 'Dinis Cottage';`

`SELECT sqlcipher_export('encrypted'); `

`DETACH DATABASE encrypted;`

# Decryption Guide

``` shell
$ cd ~/;
$ ./sqlcipher encrypted.db 
sqlite> PRAGMA key = 'testkey'; 
sqlite> ATTACH DATABASE 'plaintext.db' AS plaintext KEY '';  -- empty key will disable encryption
sqlite> SELECT sqlcipher_export('plaintext'); 
sqlite> DETACH DATABASE plaintext;
```

