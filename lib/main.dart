import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
        // This makes the visual density adapt to the platform that you run
        // the app on. For desktop platforms, the controls will be smaller and
        // closer together (more dense) than on mobile platforms.
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: 'Flutter Method Channel / Android'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel("saad.farhan/maps");

  double _latitude = -34.0;
  double _longitude = 151.0;

  Future<void> _getMapActivity() async {
    try {
      await platform.invokeMethod(
          'getMapActivity', {'latitude': _latitude, 'longitude': _longitude});
    } on PlatformException catch (e) {
      print(e.message);
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    platform.setMethodCallHandler(_androidCallback);
  }

  Future<void> _androidCallback(MethodCall call) async {
    if (call.method == "locationUpdated") {
      setState(() {
        _latitude = call.arguments['latitude'];
        _longitude = call.arguments['longitude'];
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text("Latitude : $_latitude"),
            Text("Longitude : $_longitude"),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async{
          _getMapActivity();
        },
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
}
