import React from "react";
import "./Home.css";

function Home(props) {
  console.log(props);
  return (
    <div className="home__container">
      <span>
        “안녕”
      </span>
      <span>− 다진, 현아</span>
    </div>
  );
}

export default Home;
