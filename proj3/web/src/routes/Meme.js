import React from 'react'
import {Grid} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles'
import Card from '../components/Card'


const useStyles = makeStyles({
    gridContainer: {
        paddingLeft: '20px',
        paddingRight: '20px'
    },
    card: {
        width: '200px',
        height: '200px'
    }
})

function Meme() {
    const classes = useStyles();
    return (
        //grid container를 사용하면 전체를 차지하지 않게 된다 gutter 만들때 사용
        <Grid container="container" className={classes.gridContainer} spacing={4}>            
                <Card className={classes.card}/>
        </Grid>
    )
}

export default Meme

