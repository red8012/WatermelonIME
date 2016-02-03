# Watermelon IME

### TODO

1. reorganize dict (s1, s2, s3, …)
2. hide first candidate right
3. candidate first line height

### Benchmark Result

``` python
     cold start   warm start  comment
goal    1000          500
2/3     3064         2063
```



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
12. [done] ATTACH DATABASE ':memory:' AS aux1; —> 2.5 s can query during copy [OK] 30%~100% improvement
13. raw query bind variable
14. set thread priority

``` 
ATTACH DATABASE 'file::memory:?cache=shared' AS m KEY '';
```