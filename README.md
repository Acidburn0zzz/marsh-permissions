marsh-permissions
===

Android app to manage apps that target Marshmallow. Displays a list of app packages and allows to 
navigate to the App Info screen for each of them. This is a simple app that I made simply because
I needed it.

The project follows a [Clean Architecture][1] design. Entities lie at the innermost level containing
 enterprise business rules. Interactors follow containing application business rules. The next level
 contains adapters like presenters or repositories. The last layer contains frameworks including
 the Android UI, SharedPreferences etc. Inner layers do not depend on outer layers and they only
 reference interfaces which the outer layers can implement.

The presentation layer is organized with the MVP pattern.

Some libraries used in this project are are Dagger 2 for dependency injection and Rx-Java for 
reactive programming.

Download
===

The app is available for download in [Google Play][2].

License
===

    Copyright 2016 Ivan Soriano

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]:http://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html
[2]:https://play.google.com/store/apps/details?id=com.guavabot.marshpermissions