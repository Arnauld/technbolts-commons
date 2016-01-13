# technbolts-serializer

Add annotations to ease XStream serialization/deserialization with versionning

* property name changed between version
* field has been removed since/until a version
* field has been created since/until a version
* nested object must be in a particular version
* ...

Example for class with nested class

```java
@Aliases({
@Alias(value="parent_data", until= Version.V1),
@Alias(value="parentData", since=Version.V2)})
@Ignore
public class TestParentData
{
  @Aliases({
    @Alias(value="creation_date", since=Version.V0, until=Version.V1),
    @Alias(value="creation_datetime", since=Version.V2)
  })
  private Date creationDate;
  
  @Since(Version.V2)
  @Alias("extra")
  @Require(Version.V1)
  private TestData infos;

...
}
```

with nested class:

```java
@Aliases({
@Alias(value="test_data", until= Version.V1),
@Alias(value="data", since=Version.V2)})
@Ignore
public class TestData
{
  @Since(Version.V0)
  @Aliases({
    @Alias(value="equality", until=Version.V0),
    @Alias(value="equals", since=Version.V1)})
  private int count;

  @Aliases({
  @Alias(value="name", until=Version.V1),
  @Alias(value="fullname", since=Version.V2)})
  private String name;

  @Aliases({
    @Alias(value="id", until=Version.V0),
    @Alias(value="identifier", since=Version.V1, until=Version.V1),
    @Alias(value="uuid", since=Version.V2)})
  @AsAttribute(since=Version.V2)
  private String id;

...
}
```

Configure the serializer to serialize/deserialize `TestData` in **v0**

```java
XStreamSerializer serializer = new XStreamSerializer ();
serializer.recursivelyProcessAnnotations(TestData.class, Version.V0);
```

Configure the serializer to serialize/deserialize `TestParentData` in **v2**.
This will automatically configure the serializer/deserializer for `TestData` in **v1**.

```java
XStreamSerializer serializer = new XStreamSerializer ();
serializer.recursivelyProcessAnnotations(TestParentData.class, Version.V2);
```

```java
TestData data = ...
String xmlData = serializer.toXml(data);
```

