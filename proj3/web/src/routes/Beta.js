import React from 'react'
import axios from 'axios';
import "./Beta.css";

class Beta extends React.Component {
  
  constructor(props){
    super(props);
    this.state = {
      file: null,
      filename: null,
      isLoading: true,
      memes: [],
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
      formData.append('selected_image', this.state.file);
      const config = {
          headers: {
              'content-type': 'multipart/form-data'
          }
      };

      // call post
      axios.post("http://localhost:11000/upload", formData, config)
          .then((response) => {
              //alert("successfully uploaded and success : " + String(response["data"]["wines"]));
              alert("successfully uploaded" + String(JSON.stringify(response)));
              this.setState({ wines: response["data"]["wines"], isLoading: false });
          }).catch((error) => {
              alert("fail to upload image" + error);
          }
      );
  }


  render() {
    const { file, isLoading, wines} = this.state;
    return (
        <section className = "container">
          Hello Beta
        </section>
    )
  }
}

export default Beta

