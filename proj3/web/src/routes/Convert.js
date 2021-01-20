import React from 'react'
import axios from 'axios';
import "./Transform.css";

class Convert extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      targetFilename: props.match.params.name,
      file: null,
	  filename: null,
      isLoading: true,
      result_file: "http://192.249.18.233:5000/results/result_meme.jpg"
    };
    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  onChange = async(e) =>{
    this.setState({file:e.target.files[0]})
  }

  onFormSubmit = async(e) => {
      // prevent default action
      e.preventDefault();

      // set params
      const formData = new FormData();
      formData.append('source', this.state.file);
      formData.append('target', this.state.file);
      const config = {
          headers: {
              'content-type': 'multipart/form-data'
          }
      };

      // call post
	 console.log(this.state.domain)
	axios.post("http://192.249.18.233:5000/meme", formData, config)
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
              <div className="transform__container">
                <form onSubmit={this.onFormSubmit}>
                  <h2>  {this.state.domain} </h2>
                  <input type="file" name="source" onChange={this.onChange}/>
                  <button type="submit">Search</button>
                </form>
              </div>

				<img src={"images/"+this.state.targetFilename} />
          {isLoading ? (
			<div> Loading...</div>
          ) : (
			<img 
				src={this.state.result_file}
				alt="new"
			/>
		  )}
        </section>
    )
  }
}

export default Convert

