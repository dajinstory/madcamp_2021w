import React from "react";
import { Link } from "react-router-dom";
import "./Navigation.css";

function Navigation() {
  return (
    <div className="nav">
      <Link to="/">Home</Link>
      <Link to="/service">Service</Link>
      <Link to="/meme">Meme</Link>
      <Link to="/beta">Beta</Link>
    </div>
  );
}

export default Navigation;
