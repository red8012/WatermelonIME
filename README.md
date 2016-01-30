# Watermelon IME

### TODO

1. [done] character lock
2. [done] punctuation
3. [done] candidate select
4. dict select
5. [done] zh, ch, sh, r, zi, ci , si, wui
6. [done] candidate should fill both line when only one character typed
7. reorganize dict (s1, s2, s3, …)
8. [done] crash when typing fast
9. hide first candidate right
10. candidate first line height
11. restore candidate when dict close

### Performance TODO

1. benchmark
2. database warm up
3. drop index + vacuum and create index after first run (38% space saving)
4. drop unused columns
5. use covering index (expected ~2X faster)
6. PRAGMA automatic_index; default on 3.7.17
7. PRAGMA schema.integrity_check
8. PRAGMA schema.synchronous = 0 | OFF
9. PRAGMA schema.locking_mode = EXCLUSIVE
10. PRAGMA temp_store = 2 | MEMORY
11. PRAGMA mmap_size=268435456;
12. ATTACH DATABASE ':memory:' AS aux1;
13. raw query bind variable
14. set thread priority