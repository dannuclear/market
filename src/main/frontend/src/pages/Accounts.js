import React, { useMemo, useState } from 'react'
import ServerDataGrid from '../components/ServerDataGrid'
import AccountDialog from '../components/account/AccountDialog'
import InfoDialog from '../components/account/InfoDialog'
import { IconButton, TextField } from '@mui/material'
import AgreeDisagreeDialog from '../components/AgreeDisagreeDialog'
import axios from '../api/axios'

const Accounts = () => {

    const [account, setAccount] = useState(null)

    const [payInId, setPayInId] = useState()
    const [payInValue, setPayInValue] = useState(10000)

    const onInfo = (id) => {
        setAccount({ id: id })
    }

    const onPayIn = (id) => {
        setPayInId(id)
    }

    const onOkPayIn = () => {
        axios.post(`accounts/${payInId}/payIn?value=${payInValue}`)
            .then(resp => setPayInId(null))
    }

    const columns = useMemo(() => [
        { field: 'id', headerName: 'id', width: 300 },
        { field: 'name', headerName: 'Наименование', flex: .1 },
        { field: 'type', headerName: 'Тип', flex: .1 },
        { field: 'status', headerName: 'Статус', flex: .1 },
        { field: 'openedDate', headerName: 'Открыт', flex: .1 },
        { field: 'closedDate', headerName: 'Закрыт', flex: .1 },
        { field: 'accessLevel', headerName: 'Уровень', flex: .2 },
        {
            field: 'payIn', headerName: '', width: 10, sortable: false, renderCell: p => <IconButton variant='outlined' size='small' onClick={() => onPayIn(p.row.id)} title='Пополнить'><i className="fa-thin fa-plus fa-lg"></i></IconButton>
        },
        {
            field: 'info', headerName: '', width: 10, sortable: false, renderCell: p => <IconButton variant='outlined' size='small' onClick={() => onInfo(p.row.id)} title='Инфо'><i className="fa-thin fa-bolt fa-lg"></i></IconButton>
        },
    ])

    return (
        <>
            <ServerDataGrid
                columns={columns}
                path='accounts'
                name='Счет'
                EditDialog={AccountDialog}
                showEdit={false}
            />

            <InfoDialog
                item={account}
                title='Инфо'
                onCancel={() => setAccount(null)}
                onOk={() => setAccount(null)}
            ></InfoDialog>

            <AgreeDisagreeDialog
                title='Пополнить'
                open={!!payInId}
                onDisagree={() => setPayInId(null)}
                onAgree={onOkPayIn}
                maxWidth='sm'
                content={
                    <TextField value={payInValue} onChange={event => setPayInValue(event.target.value)}></TextField>
                } />
        </>
    )
}

export default Accounts