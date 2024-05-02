import React from 'react'
import ServerDataGrid from '../components/ServerDataGrid'
import AccountDialog from '../components/account/AccountDialog'

const columns = [
    { field: 'id', headerName: 'id', flex: 0.2 },
    { field: 'name', headerName: 'Наименование', flex: .2 },
    { field: 'type', headerName: 'Тип', flex: .2 },
    { field: 'status', headerName: 'Статус', flex: .2 },
    { field: 'openedDate', headerName: 'Открыт', flex: .1 },
    { field: 'closedDate', headerName: 'Закрыт', flex: .1 },
    { field: 'accessLevel', headerName: 'Уровень', flex: .2 },
]

const Accounts = () => {

    return (
        <>
            <ServerDataGrid
                columns={columns}
                path='accounts'
                name='Счета'
                EditDialog={AccountDialog}
            />
        </>
    )
}

export default Accounts