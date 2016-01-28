# Watermelon IME

### TODO

1. character lock
2. candidate select
3. zh, ch, sh, r, zi, ci , si
4. reorganize dict

### Performance TODO

1. benchmark
2. database warm up
3. drop index + vacuum and create index after first run (38% space saving)
4. use covering index (expected ~2X faster)
5. PRAGMA automatic_index; default on 3.7.17
6. PRAGMA schema.integrity_check
7. PRAGMA schema.synchronous = 0 | OFF
8. PRAGMA schema.locking_mode = EXCLUSIVE
9. PRAGMA temp_store = 2 | MEMORY
10. PRAGMA mmap_size=268435456;
11. ATTACH DATABASE ':memory:' AS aux1;
12. raw query bind variable