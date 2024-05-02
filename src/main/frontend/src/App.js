import { Alert, Container, Snackbar, Typography } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import 'dayjs/locale/ru';
import { useState } from 'react';
import { Route, Routes, useNavigate } from 'react-router-dom';
import AppBarStyled from './AppBarStyled';
import './assets/css/all.min.css';
import { MessageContext } from './components/MessageContext';
import Instruments from './pages/Instruments';
import Accounts from './pages/Accounts';
// import GrantTypes from './pages/GrantTypes';
// import PaymentAgents from './pages/PaymentAgents';
// import Payments from './pages/Payments';
// import Persons from './pages/Persons';
// import StudyGroups from './pages/StudyGroups';

function App() {
  const [messageState, setMessageState] = useState({ open: false, message: '', severity: 'success' })

  const showMessage = (text, severity = 'success', duration = 5000) => setMessageState({ open: true, message: text, severity, duration })
  const messageOnClose = (event, reason) => {
    if (reason === 'clickaway')
      return
    setMessageState({ ...messageState, open: false })
  }

  return (
    <>
      <Snackbar anchorOrigin={{ horizontal: 'right', vertical: 'top' }} open={messageState.open} autoHideDuration={messageState.duration} onClose={messageOnClose}>
        <Alert severity={messageState.severity} sx={{ width: '100%' }} onClose={messageOnClose}>
          {messageState.message}
        </Alert>
      </Snackbar>

      <MessageContext.Provider value={showMessage}>
        <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale='ru'>

          <Container maxWidth="false" disableGutters sx={{ mt: 1}}>
            <AppBarStyled />
            <Routes>
              <Route path='instruments' element={<Instruments />} />
              <Route path='accounts' element={<Accounts />} />
              {/* <Route path='grantTypes' element={<GrantTypes />} />
              <Route path='studyGroups' element={<StudyGroups />} />
              <Route path='paymentAgents' element={<PaymentAgents />} />
              <Route path='payments' element={<Payments />} />
              <Route path='calculations' element={<Calculations />} /> */}
              <Route path='*' element={<Typography>Добро пожаловать</Typography>} />
            </Routes>
          </Container>

        </LocalizationProvider>
      </MessageContext.Provider>
    </>
  );
}

export default App;
