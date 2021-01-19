import React, { Component } from 'react'
import StackGrid, {transitions} from 'react-stack-grid'
import { Link } from 'react-router-dom'
import PropTypes from 'prop-types'
import ServiceLink from "../components/ServiceLink"

class Service extends Component {
    render(){
        return (
            <StackGrid columnWidth='25%'>
                {Example.map(ex => 
                    <ServiceLink 
                        key={ex.id} 
                        id={ex.id}
                        name={ex.name} 
                        image={ex.image}/>
                )}
            </StackGrid>
        )
    }
}

const Example = [
    {
        id: "1",
        name: "DET",
        image: "https://c5.staticflickr.com/9/8768/28941110956_b05ab588c1_b.jpg"
    },
    {
        id: "2",
        name: "SOD", 
        image: "https://c3.staticflickr.com/9/8583/28354353794_9f2d08d8c0_b.jpg"
    },
    {
        id: "3",
        name: "SEG",
        image: "https://c7.staticflickr.com/9/8569/28941134686_d57273d933_b.jpg"
    },
    {
        id: "4",
        name: "GAN",
        image: "https://c6.staticflickr.com/9/8520/28357073053_cafcb3da6f_b.jpg"
    }
]

export default Service
