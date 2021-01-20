// import libraries
import React from "react";
import { HashRouter, Route } from "react-router-dom";

// main pages, components, and css file
import Home from "./routes/Home";
import Service from "./routes/Service";
import Meme from "./routes/Meme";
import Beta from "./routes/Beta";
import Transform from "./routes/Transform";
import Convert from "./routes/Convert";
import Navigation from "./components/Navigation";
import "./App.css";

function App() {
  return (
    <HashRouter>
      <Navigation />
      <Route path="/" exact={true} component={Home} />
      <Route path="/service" component={Service} />
      <Route path="/meme" component={Meme} />
      <Route path="/beta" component={Beta} />
      <Route path="/editor/:name" component={Transform} />
      <Route path="/converter/:name" component={Convert} />
    </HashRouter>
  );
}

export default App;
