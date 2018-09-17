import {createMuiTheme} from '@material-ui/core/styles';

const theme = createMuiTheme({
  palette: {
    primary: {
      light: '#f28e91',
      main: '#e41c23',
      dark: '#e41c23',
    },
    secondary: {
      light: '#e5e5e5',
      main: '#FFF',
      dark: '#9e9e9e',
      contrastText: '#e41c23',
    },
  },
  typography: {
    fontFamily: 'Source Sans Pro, sans-serif',
  },
  overrides: {
    MuiTableHead: {
      root: {
         backgroundColor: '#f3f3f3',
         textTransform: 'uppercase !important',
      },
    },
    MuiTableSortLabel: {
      root: {
        letterSpacing: 1.4,
        fontWeight: 700,
      },
    },
  },
});

export default theme;
