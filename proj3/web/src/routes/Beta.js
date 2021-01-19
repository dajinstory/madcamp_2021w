import React from 'react'
import axios from 'axios';
import "./Beta.css"

const baseStyle = {
    width: '100%',
    height: "100%",
    flexDirection: "column",
    alignItems: "center",
    borderWidth: 2,
    borderRadius: 2,
    borderColor: "#eeeeee",
    borderStyle: "dashed",
    backgroundColor: "#fafafa",
    color: "#bdbdbd",
    outline: "none",
    transition: "border .24s ease-in-out",
    textAlign: "center"
}

const activeStyle = {
    borderColor: "#2196f3"
}

const acceptStyle = {
    borderColor: "#00e676"
};

const rejectStyle = {
    borderColor: "#ff1744"
};

const thumbsContainer = {
    display: "flex",
    flexDirection: "row",
    flexWrap: "wrap",
    marginTop: 16
};

const thumb = {
    //    display: "inline-flex",
    width: '100%',
    height: "300px",
    borderRadius: 2,
    border: "1px solid #eaeaea",
    marginBottom: 8,
    marginRight: 8,
    width: 400,
    height: 200,
    padding: 4,
    boxSizing: "border-box"
};

const thumbInner = {
    display: "flex",
    minWidth: 0,
    overflow: "hidden"
};

const img = {
    display: "block",
    width: "200px",
    height: "auto"
};

class Beta extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      domain: "meme",
      sFile: null,
      tFile: null,
      sFilename: null,
      tFilename: null,
      result_file: null,
    };
    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onChangeS = this.onChangeS.bind(this);
    this.onChangeT = this.onChangeT.bind(this);
  }

  onChangeS = async(e) =>{
    this.setState({
		sFile:e.target.files[0],
	})
  }
  onChangeT = async(e) =>{
    this.setState({
		tFile:e.target.files[0],
	})
  }

  onFormSubmit = async(e) => {
      // prevent default action
      e.preventDefault();

      // set params
      const formData = new FormData();
      formData.append('source', this.state.sFile);
      formData.append('target', this.state.tFile);
      const config = {
          headers: {
              'content-type': 'multipart/form-data'
          }
      };

      // call post
	 console.log(this.state.domain)
	axios.post("http://192.249.18.233:5000/"+this.state.domain.toLowerCase(), formData, config)
          .then((response) => {
              alert("successfully uploaded");
          }).catch((error) => {
              alert("looks succesful");
          }
      );
  }


  render() {
    const { file, memes} = this.state;
    return (
        <section className = "container">
        	<div className="beta__container">
            	<form onSubmit={this.onFormSubmit}>
                  <h2>  {this.state.domain} </h2>
                  <input type="file" name="source" onChange={this.onChangeS}/>
                  <input type="file" name="target" onChange={this.onChangeT}/>
                  <button type="submit">Search</button>
                </form>
              </div>
        </section>
    )
  }
}

export default Beta

