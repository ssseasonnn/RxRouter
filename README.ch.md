# RxRouter

![](https://img.shields.io/badge/language-kotlin-brightgreen.svg) ![](https://img.shields.io/badge/RxJava-2.0-blue.svg) [![Download](https://api.bintray.com/packages/ssseasonnn/android/RxRouter/images/download.svg)](https://bintray.com/ssseasonnn/android/RxRouter/_latestVersion)


*Read this in other languages: [中文](README.ch.md), [English](README.md)* 

一个轻量级、简单、智能并且强大的安卓路由库


## Getting started

### 添加依赖

在build.gradle文件中添加以下依赖：

```groovy
dependencies {
	implementation 'zlc.season:rxrouter:x.y.z'
	annotationProcessor 'zlc.season:rxrouter-compiler:x.y.z'
}
```

(替换上面的 `x` 、 `y` 和 `z`为最新的版本号:[![](https://api.bintray.com/packages/ssseasonnn/android/RxRouter/images/download.svg)](https://bintray.com/ssseasonnn/android/RxRouter/_latestVersion))

如果使用 `Kotlin` ，用 `kapt` 替换 `annotationProcessor`

### Hello World

首先在我们需要路由的Activity上添加 `@Url` 注解：

```kotlin
@Url("this is a url")
class UrlActivity : AppCompatActivity() {
    ...
}
```

然后创建一个被 `@Router` 注解的类，用来告诉RxRouter这里有一个路由器：

```Kotlin
@Router
class MainRouter{
}
```

这个类不需要有任何其余的代码，RxRouter会根据这个类的类名自动生成一个 `RouterProvider` ，比如这里的 `MainRouter` 将会生成 `MainRouterProvider` .

接着我们需要把这些路由器添加到 `RxRouterProviders` 中：

```kotlin
class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RxRouterProviders.add(MainRouterProvider())
    }
}
```

最后，就可以开始我们的表演了：

```kotlin
RxRouter.of(context)
        .route("this is a uri")
        .subscribe()
```

### 参数传递

携带参数跳转：

通过with方法，你可以给本次路由添加一系列参数.

```kotlin
RxRouter.of(context)
        .with(10)                         	//int value
        .with(true)							//boolean value
        .with(20.12)						//double value
        .with("this is a string value")		//string value
        .with(Bundle())						//Bundle value
        .route("this is a uri")
        .subscribe()
```



### 不再需要 `onActivityResult` 方法了

想要获取跳转返回的值？再也不用写一大堆 `onActivityResult` 方法了！链式调用，一步到位！

```kotlin
RxRouter.of(context)
		.with(false)
        .with(2000)
        .with(9999999999999999)
        .route("this is a uri")
        .subscribe {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                val stringResult = intent.getStringExtra("result")
                result_text.text = stringResult
                stringResult.toast()
            }
        }
```

如果有结果返回，在subscribe中处理就行了.



### Class 跳转

不想用Url注解？没问题，RxRouter同样支持原始的指定类名的跳转方式，和url跳转的方式相同：

```kotlin
RxRouter.of(context)
        .routeClass(ClassForResultActivity::class.java)
        .subscribe{
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                val stringResult = intent.getStringExtra("result")
                result_text.text = stringResult
                stringResult.toast()
            }
        }
```



### Action 跳转

同样的，RxRouter也支持系统的Action和自定义的Action跳转.

自定义Action跳转：

```Xml
<activity android:name=".ActionActivity">
    <intent-filter>
        <action android:name="zlc.season.sample.action" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

```kotlin
RxRouter.of(context)
        .routeAction("zlc.season.sample.action")
        .subscribe({
            "no result".toast()
        }, {
            it.message?.toast()
        })
```

系统Action跳转：

```kotlin
//拨打电话
RxRouter.of(this)
        .addUri(Uri.parse("tel:123456"))
        .routeSystemAction(Intent.ACTION_DIAL)
        .subscribe()

//发送短信
val bundle = Bundle()
bundle.putString("sms_body", "这是信息内容")
RxRouter.of(this)
        .addUri(Uri.parse("smsto:10086"))
        .with(bundle)
        .routeSystemAction(Intent.ACTION_SENDTO)
        .subscribe()
```



### 防火墙

RxRouter拥有一个小巧而强大的防火墙，能够在路由之前根据防火墙的规则进行拦截，您可以添加一个或者多个防火墙.

```kotlin
//创建一个LoginFirewall
class LoginFirewall : Firewall {
    override fun allow(datagram: Datagram): Boolean {
        if (notLogin) {
            "您还没有登录，请先登录".toast()
            return false
        }
        return true
    }
}

//将Firewall添加到路由中
RxRouter.of(this)
        .addFirewall(LoginFirewall())
        .route("this is a url")
        .subscribe()
```


### License

> ```
> Copyright 2018 Season.Zlc
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```