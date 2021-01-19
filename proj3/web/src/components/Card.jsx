import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { Link } from 'react-router-dom'

const useStyles = makeStyles({
    root: {
        minWidth: 275,
        margin: '20px'
    },
    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)'
    },
    title: {
        fontSize: 14
    },
    pos: {
        marginBottom: 12
    }
});

function Image({id, image, mood}) {
    const classes = useStyles();
    return (
        <Link style={{ textDecoration: 'none' }}
            to={{
                pathname: `/service/${id}`,
                state: {
                    id: id,
                    image: image,
                    mood: mood
                }
            }}>

            <Card className={classes.root} variant="outlined">
                <CardContent>
                    <Typography
                        className={classes.title}
                        color="textSecondary"
                        gutterBottom="gutterBottom">
                        {id}
                    </Typography>
                    <Typography variant="h5" component="h2">
                        {mood}
                    </Typography>
                    <Typography className={classes.pos} color="textSecondary">
                        {mood}
                    </Typography>
                    <Typography variant="body2" component="p">
                    <img src={image} height="200px" width="auto" alt="test" />
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button size="small">
                        <img src="img/btn/heart.png" alt="test" width="20px" height="20px"/>
                    </Button>
                    <Button size="small">
                        <img src="img/btn/download.png" alt="test" width="20px" height="20px"/>
                    </Button>
                </CardActions>
            </Card>
        </Link>
    )
}

export default function OutlinedCard() {

    return (
        
            Example.map(ex => 
                    <Image 
                    key={ex.image} 
                    image={ex.image} 
                    id={ex.id} mood={ex.mood} ></Image>    
                )
    )
    
}

const Example = [
    {
        id: "1",
        image: "images/goguma.jpg",
        mood: "happy"
    },
    {
        id: "2",
        image: "images/love.jpg",
        mood: "sad"
    },
    {
        id: "3",
        image: "images/ms.jpg",
        mood: "soso"
    },
    {
        id: "4",
        image: "images/orange.jpg",
        mood: "bad"
    },
    {
        id: "5",
        image: "images/misun.jpg",
        mood: "happy"
    },
    {
        id: "6",
        image: "images/kwon.jpg",
        mood: "sad"
    },
    {
        id: "7",
        image: "images/jinyoung.jpg",
        mood: "soso"
    },
    {
        id: "8",
        image: "images/hagisiru.jpg",
        mood: "bad"
    },
    {
        id: "9",
        image: "images/gomabda.jpg",
        mood: "happy"
    }
]
