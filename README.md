# Reproducer for a generated inner class import bug in ECJ 3.35 and newer

* `mvn -Pecj336 clean compile` to reproduce the bug. The import `org.example.GenerateInnerClassTriggerGen` cannot be
  resolved. Uses ECJ 3.36, the same happens also for ECJ 3.35.
* `mvn -Pecj334 clean compile` to show that the bug is not in ECJ 3.34. The
  import `org.example.GenerateInnerClassTriggerGen` is resolved just fine.
* `mvn clean compile` to use the javac compiler instead of the eclipse compiler. The
  import `org.example.GenerateInnerClassTriggerGen` is also resolved just fine.

Interestingly enough, the bug is not reproducible if the unused annotation definition `OtherAnnotation` in the
class `org.example.GenerateInnerClassTrigger` is removed.