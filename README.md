# RxRouter

![](https://img.shields.io/badge/language-kotlin-brightgreen.svg) ![](https://img.shields.io/badge/RxJava-2.0-blue.svg) [![Download](https://api.bintray.com/packages/ssseasonnn/android/RxRouter/images/download.svg)](https://bintray.com/ssseasonnn/android/RxRouter/_latestVersion)


*Read this in other languages: [中文](README.ch.md), [English](README.md)* 

A lightweight, simple, smart and powerful Android routing library.


## Getting started

### Setting up the dependency

Add to your project build.gradle file:

```groovy
dependencies {
	implementation 'zlc.season:rxrouter:x.y.z'
	annotationProcessor 'zlc.season:rxrouter-compiler:x.y.z'
}
```

(Please replace `x` and `y` and `z` with the latest version numbers: [![](https://api.bintray.com/packages/ssseasonnn/android/RxRouter/images/download.svg)](https://bintray.com/ssseasonnn/android/RxRouter/_latestVersion))

For `Kotlin` you should use `kapt` instead of `annotationProcessor`



### Hello World

First add the `@url` annotation to the activity we need to route:

```kotlin
@Url("this is a url")
class UrlActivity : AppCompatActivity() {
    ...
}
```

Then create a class that is annotated with `@Router` to tell RxRouter that there is a router:

```Kotlin
@Router
class MainRouter{
}
```

This class doesn't need to have any remaining code. RxRouter will automatically generate a `RouterProvider`
based on the class name of the class. For example, `MainRouter` will generate `MainRouterProvider`.

Then we need to add these routers to `RxRouterProviders`:

```kotlin
class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RxRouterProviders.add(MainRouterProvider())
    }
}
```

Finally, we can start our performance:

```kotlin
RxRouter.of(context)
        .route("this is a uri")
        .subscribe()
```

### Parameters

Routing with parameters:

With the `with` method, you can add a series of parameters to this route.

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



### No longer needs the `onActivityResult` method

Want to get the value returned by the route?  No longer have to write a bunch of `onActivityResult` methods! One step in place with RxJava！

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

If there is a result return, it will be processed in subscribe.


### Routing through Class

Do not want to use Url annotations? No problem, RxRouter also supports the original jump of the specified class name, in the same way:

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



### Routing through Action

Similarly, RxRouter also supports system Actions and custom Actions.

Routing through custom Action:

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

Routing through system action:

```kotlin
//dial
RxRouter.of(this)
        .addUri(Uri.parse("tel:123456"))
        .routeSystemAction(Intent.ACTION_DIAL)
        .subscribe()

//send message
val bundle = Bundle()
bundle.putString("sms_body", "this is message")
RxRouter.of(this)
        .addUri(Uri.parse("smsto:10086"))
        .with(bundle)
        .routeSystemAction(Intent.ACTION_SENDTO)
        .subscribe()
```



### Firewall

The RxRouter has a small and powerful firewall that can be intercepted according to firewall rules before routing. You can add one or more firewalls.

```kotlin
//create a LoginFirewall
class LoginFirewall : Firewall {
    override fun allow(datagram: Datagram): Boolean {
        if (notLogin) {
            "please log in".toast()
            return false
        }
        return true
    }
}

//Add Firewall to Route
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