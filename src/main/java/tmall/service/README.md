# 知识点

## 路径参数和url参数
@PathVariable是获取路径参数，获取包含在路径{id}里头的参数
@RequestParam是获取url参数

## 返回值
Page4Navigator是Page的封装，类似list容器，<>内的东西表示容器装的类型(即content字段值列表里装的对象类型)
返回值示例

``` json
{"navigatePages":5,
"totalPages":4,
"number":0,
"totalElements":19,
"size":5,
"numberOfElements":5,
"content":[{"id":105,"name":"111"},{"id":104,"name":"Fred"},{"id":83,"name":"平板电视"},{"id":82,"name":"马桶"},{"id":81,"name":"沙发"}],
"first":true,
"last":false,
"navigatepageNums":[1,2,3,4],
"hasContent":true,
"hasNext":true,
"hasPrevious":false}
```

## 关系图

``` mermaid
graph TD;
C[Category分类]--一对多-->Pt[property属性名];
P[product产品]--一对多-->Pv[propertyValue属性值];
Pt--一对多-->Pv;

```