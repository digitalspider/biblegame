import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import './App.css'

const styles = theme => ({
  root: {
    flexGrow: 1
  },
  paper: {
    padding: theme.spacing.unit * 2,
    textAlign: "center",
    color: theme.palette.text.secondary
  },
  statsTable: {
    maxWidth: "10%",
  },
});

function App(props) {
  const { classes } = props;

  return (
    <div className={classes.root}>
      <Grid
        container
        spacing={8}
        direction="row"
        justify="space-between"
        alignItems="stretch"
      >
        <Grid item xs>
          <Paper className={classes.paper}>
            <h1>Map</h1>
            <List>
              <ListItem>
                <ListItemText>Mount</ListItemText>
              </ListItem>
              <ListItem>
                <Grid container direction="row" justify="center">
                  <ListItem>
                    <ListItemText>Library</ListItemText>
                  </ListItem>
                  <ListItem>
                    <ListItemText>Markets</ListItemText>
                  </ListItem>
                  <ListItem>
                    <ListItemText>Field</ListItemText>
                  </ListItem>
                </Grid>
              </ListItem>
              <ListItem>
                <ListItemText>Street</ListItemText>
              </ListItem>
              <ListItem>
                <ListItemText>Home</ListItemText>
              </ListItem>
            </List>
          </Paper>
        </Grid>
        <Grid item xs={6}>
          <Paper className={classes.paper}>
            BibleGame
          </Paper>
          <Paper className={classes.paper}>
            What would you like to do next?
          </Paper>
          <form className={classes.container} noValidate autoComplete="off">
            <Grid>
              <Button variant="outlined" color="primary">
                Study
              </Button>
              <Button variant="outlined" color="primary">
                Work
              </Button>
              <Button variant="outlined" color="primary">
                Pray
              </Button>
              <Button variant="outlined" color="primary">
                Ask
              </Button>
              <Button variant="outlined" color="primary">
                Give
              </Button>
            </Grid>
            <TextField
              id="outlined-name"
              label="Input Command"
              className="input"
              value="Test"
              margin="normal"
              variant="outlined"
            />
          </form>
        </Grid>
        <Grid item xs>
          <Paper className={classes.paper}>
            <h4>Stats</h4>
            <Table className={classes.statsTable}>
              <TableRow>
                <TableCell>Stamina</TableCell>
                <TableCell>12</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Love</TableCell>
                <TableCell>1</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Knowledge</TableCell>
                <TableCell>1</TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Riches</TableCell>
                <TableCell>1</TableCell>
              </TableRow>
            </Table>
          </Paper>
        </Grid>
      </Grid>
    </div>
  );
}

App.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(App);
