### DEMO 09 - DEADLOCK

#### Version A

A simple demo of deadlock: Forgetting to release mutex.

&nbsp;

#### Version B

There are 2 workers "foo" and "bar".

They try to access resource A and B (which are protected by mutResourceA and mutResourceB).

Scenario:

```text
foo():
    synchronized:
        access resource A

        synchronized:
            access resource B

bar():
    synchronized:
        access resource B

        synchronized:
            access resource A
```

After foo accessing A and bar accessing B, foo and bar might wait other together ==> Deadlock occurs.