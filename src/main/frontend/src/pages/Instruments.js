import React from 'react'
import ServerDataGrid from '../components/ServerDataGrid'
//import StudyGroupDialog from '../components/studyGroup/StudyGroupDialog'

const columns = [
    { field: 'type', headerName: 'Отделение', flex: 0.1, valueGetter: p => (p.row.type == 0) ? 'ВО' : (p.row.type == 1) ? 'СПО' : '' },
    { field: 'name', headerName: 'Наименование', flex: 1 },
]

const Instruments = () => {

    return (
        <>
            <ServerDataGrid
                columns={columns}
                path='instruments'
                name='Инструменты'
                //EditDialog={StudyGroupDialog}
            />
        </>
    )
}

export default Instruments