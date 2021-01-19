import React, {useMemo, useEffect, useState} from 'react'
import {useDropzone} from "react-dropzone"
import axios from 'axios'

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

function TakeImg() {
    const [files, setFiles] = useState([])
    const [isLoad, setLoads] = useState(false)
    const {
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
                acceptedFiles.map(file => Object.assign(file, 't', {preview: URL.createObjectURL(file)}))
            )
        }

    })

    //스타일 지정
    //의존성이 변경되었을 때 메모된 값만 다시 계산 (렌더링 중에 실행)
    const style = useMemo(() => ({
        ...baseStyle,
        ...(
            isDragActive
                ? activeStyle
                : {}
        ),
        ...(
            isDragAccept
                ? acceptStyle
                : {}
        ),
        ...(
            isDragReject
                ? rejectStyle
                : {}
        )
    }), [isDragActive, isDragReject]
    ,console.log(files));
     //이것들이 변하면 component를 re-render하겠다))
    
     const thumbs = files.map(file => (
        <div style={thumb} key={file.name}>
            <div style={thumbInner}>
                <img src={file.preview} style={img}/>
            </div>
        </div>
    ));


    //렌더링이 완료된 후에 수행
    useEffect(() => () => {
        // Make sure to revoke the data uris to avoid memory leaks
        files.forEach(file => URL.revokeObjectURL(file.preview));
        console.log(files.preview)
    }, [files][isLoad]);

    const filepath = acceptedFiles.map(
        file => (<li key={file.path}>
            {file.path}
            - {file.size}
            bytes
        </li>)
    );

    return (
        <div className="container">

            <div
                style={{
                    float: 'left',
                    padding: '3%',
                    marginLeft: '3%',
                    marginRight: '3%',
                    marginTop: '7%',
                    marginBottom: '1%',
                    width: '17%',
                    height: '200px',
                    backgroundColor: 'powderblue',
                    fontSize: '24px',
                    overflow: 'hidden'
                }}>

                {
                    isLoad
                    //<div style={thumbsContainer}>{thumbs}</div>
                        ? (<div style={thumbsContainer}>{thumbs}</div>)
                        : (
                            <div style={{overflow: 'hidden'}}{...getRootProps({ style })}>
                                <input {...getInputProps()}/>
                                <p>Drag 'n' drop some files here</p>
                                <button type="button" onClick={open}>
                                    Open File Dialog
                                </button>
                            </div>
                        )
                }

            </div>
        </div>
    )
}

class Beta extends React.Component{

    render(){
        return (<div>
            <TakeImg/>
            <TakeImg/>
            <div
                style={{
                    float: 'left',
                    padding: '3%',
                    marginLeft: '3%',
                    marginRight: '3%',
                    marginTop: '7%',
                    marginBottom: '1%',
                    width: '17%',
                    height: '200px',
                    backgroundColor: 'powderblue',
                    fontSize: '24px'
                }} />
        
        <button
                style={{
                float: 'right',
                padding: "10px"
                }}
                type="button" onClick={this.sendImage}>test</button>
        </div>)
    }

    sendImage = () => {
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
        alert("ok")
    }

}

export default Beta
