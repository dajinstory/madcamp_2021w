import React, {useMemo, useEffect, useState} from 'react'
import axios from 'axios';
import "./Beta.css"
import {useDropzone} from "react-dropzone"

class Beta extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      domain: "meme",
      sFile: null,
      tFile: null,
      sFilename: null,
      tFilename: null,
	  isLoading: true,
      result_file: "http://192.249.18.233:5000/results/result_meme.jpg"
    };
    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onChangeS = this.onChangeS.bind(this);
    this.onChangeT = this.onChangeT.bind(this);
    this.onChangeS2 = this.onChangeS2.bind(this);
    this.onChangeT2 = this.onChangeT2.bind(this);
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

  onChangeS2 = async(e) =>{
    this.setState({
		sFile:e.target.files[0],
	})
  }
  onChangeT2 = async(e) =>{
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
			  this.setState({isLoading:false})
          }
      );
  }


  render() {
    const { file, isLoading, memes} = this.state;
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
			{isLoading ? (
				<div> Loading ... </div>
				) : (
				<img
					src = {this.state.result_file}
					alt = "new"
				/>
			)}
        </section>
    )
  }
}

export default Beta

