import React from 'react'
import axios from 'axios';
import "./Transform.css";

class Transform extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      domain: props.match.params.name,
      file: null,
      filename: null,
      isLoading: true,
      result_file: null,
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
      const config = {
          headers: {
              'content-type': 'multipart/form-data'
          }
      };

      // call post
      axios.post("http://localhost:5000/det", formData, config)
          .then((response) => {
              alert("successfully uploaded");
          }).catch((error) => {
              alert("fail to upload image" + error);
          }
      );
  }


  render() {
    const { file, isLoading, memes} = this.state;
    return (
        <section className = "container">
          {isLoading ? (
              <div className="transform__container">
                <form onSubmit={this.onFormSubmit}>
                  <h2>  {this.state.domain} </h2>
                  <input type="file" name="source" onChange={this.onChange}/>
                  <button type="submit">Search</button>
                </form>
              </div>
          ) : (
              <div> 결과~! 짜짠 </div>
          )}
        </section>
    )
  }
}

export default Transform

