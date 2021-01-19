import React, { useMemo, useEffect, useState } from 'react'
import ReactDom from "react-dom"
import { useDropzone } from "react-dropzone"

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

const activeStyle={
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
    width: "auto",
    height: "100%"
  };

function Meme(props){


    const [files, setFiles] = useState([])
    const [isLoad, setLoads] = useState(false)
    const{
        getRootProps,
        getInputProps,
        isDragActive,
        isDragAccept,
        isDragReject,
        acceptedFiles,
        open
    } = useDropzone({
        accept: "image/*",
        noClick: true,
        noKeyboard: true,
        onDrop: acceptedFiles => {
          setLoads(true)
            setFiles(   
                acceptedFiles.map(file =>
                    Object.assign(file, {
                        preview: URL.createObjectURL(file)
                    }))
            )

        }

    })

    const style = useMemo(
        () => ({
          ...baseStyle,
          ...(isDragActive ? activeStyle : {}),
          ...(isDragAccept ? acceptStyle : {}),
          ...(isDragReject ? rejectStyle : {})
        }),
        [isDragActive, isDragReject]
      );
    
      const thumbs = files.map(file => (
        <div style={thumb} key={file.name}>
          <div style={thumbInner}>
            <img src={file.preview} style={img} />
          </div>
        </div>
      ));
    
      useEffect(
        () => () => {
          // Make sure to revoke the data uris to avoid memory leaks
          files.forEach(file => URL.revokeObjectURL(file.preview));
        },
        [files]
        [isLoad]
        
      );
    
      const filepath = acceptedFiles.map(file => (
        <li key={file.path}>
          {file.path} - {file.size} bytes
        </li>
      ));
    
      return (
        <div className="container">

          <div style={{
            float:'left',
            padding:'3%',
            marginLeft:'7%',
            marginRight:'7%',
            marginTop:'7%',
            marginBottom:'1%',
            width:'30%',
            height:'400px',
            backgroundColor:'powderblue',
            fontSize: '24px'
          }}>
            {/* <p style={{textAlign:"center"}}><strong>이미지를 넣으세요 ^o^</strong></p> */}

            {isLoad ? (
              <div>{thumbs}</div>
            ):(<div {...getRootProps({ style })}>
              <input {...getInputProps()} />
              <p>Drag 'n' drop some files here</p>
              <button type="button" onClick={open}>
                Open File Dialog
              </button>
            </div>)}
            
            

          </div>

          <div style={{
            float:'left',
            padding:'3%',
            marginLeft:'7%',
            marginRight:'7%',
            marginTop:'7%',
            marginBottom:'1%',
            width:'30%',
            height:'400px',
            backgroundColor:'aliceblue'
          }}>
            <p>tlqkf2</p>
          </div>

          <div style={{
            float:'left',
            marginLeft:'7%',
            marginRight:'7%',
            width:'36%',
            fontSize: '24px',
          }}>
            <button style={{
              float:'right',
              padding:"10px"
            }} type="button" onClick={open}>
              Transfer
            </button>
          </div>

          <div style={{
            float:'left',
            marginLeft:'7%',
            marginRight:'7%',
            width:'36%',
            fontSize: '24px',
          }}>
            <button style={{
              float:'right',
              padding:"10px"
            }} type="button">
              
            </button>
          </div>

          <aside >
            <h4>Files</h4>
            <ul>{filepath}</ul>
          </aside>

          
        </div>
      )
    }


export default Meme

