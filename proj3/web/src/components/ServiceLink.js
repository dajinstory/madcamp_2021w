import React, { Component } from 'react'
import StackGrid, {transitions} from 'react-stack-grid'
import { Link } from 'react-router-dom'
import PropTypes from 'prop-types'

function ServiceLink({ id, name, image}) {
    return (
        <Link to={{
            pathname: `/editor/${name}`,
            state: {
                id: id,
                name: name,
                image: image 
            }}}>
            <div>
                <h4>{id}. {name}</h4>
                <img src={image} alt="Thumbnail" width="400px" height="400px"/>
            </div>
        </Link>
    )
}

ServiceLink.propTypes = {
    id: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
    image: PropTypes.string.isRequired,
}

export default ServiceLink
