import { ThemeProvider } from '@emotion/react';
import { createTheme } from '@mui/material';
import { grey } from '@mui/material/colors';
import { ruRU } from '@mui/material/locale';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import App from './App';

const theme = createTheme(
  {
    components: {
      MuiFormControl: {
        defaultProps: {
          size: 'small',
          variant: 'outlined',
        },

      },
      MuiDataGrid: {
        styleOverrides: {
          columnHeader: {
            borderRight: '1px solid rgba(224, 224, 224, 1)',
            backgroundColor: '#d6ecff'
          },
          columnHeaders: {
            borderTop: '1px solid rgba(224, 224, 224, 1)',
          },
          columnHeaderTitle: {
            fontWeight: 'bold'
          },
          toolbarContainer: {
            padding: 2
          }
        },
      },
      MuiTableCell: {
        styleOverrides: {
          head: {
            backgroundColor: grey[500],
            fontWeight: 'bold'
          },
        },
      },
      MuiInputBase: {
        styleOverrides: {
          root: {
            '& .Mui-disabled': {
              WebkitTextFillColor: "olive",
            },
          },
        },
      },
      MuiInputLabel: {
        styleOverrides: {
          root: {
            '&.Mui-disabled': {
              WebkitTextFillColor: "brown",
            },
          },
        },
      },
      MuiToggleButton: {
        styleOverrides: {
          root: {
            '&.Mui-selected': {
              backgroundColor: "darkcyan",
            },
          },
        },
      }
    },
  }, ruRU)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // <React.StrictMode>

  // </React.StrictMode>

  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <Routes>
        <Route path={`${process.env.PUBLIC_URL}/*`} element={<App />} />
        <Route path='*' element={<Navigate to={process.env.PUBLIC_URL} replace={true} />} />
      </Routes>
    </BrowserRouter>
  </ThemeProvider>
);
