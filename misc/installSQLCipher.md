# Installation Guide

* `git clone https://github.com/sqlcipher/sqlcipher.git`
* run sqlcipher.xcodeproj
* make
* `gcc -framework Security -DSQLITE_HAS_CODEC -DSQLCIPHER_CRYPTO_CC -DSQLITE_OS_UNIX=1 -I. -I./src -I./ext/rtree -D_HAVE_SQLITE_CONFIG_H -DBUILD_sqlite -DNDEBUG -DSQLITE_THREADSAFE=1 -DSQLITE_OMIT_LOAD_EXTENSION=1 -DHAVE_READLINE=0 -o sqlcipher ./src/shell.c  ./.libs/libsqlcipher.a`

