import { LinearProgress } from '@mui/material'
import { DataGrid } from '@mui/x-data-grid'
import React, { useCallback } from 'react'
import CustomGridActionCellItem from './CustomGridActionCellItem'

function BaseDataGrid(props) {
    const {
        columns,
        onEdit,
        onDelete,
        onPrint,
        onClone,
        toolbar,
        actions = [],
    } = props

    const onCloneInternal = useCallback((id) => () => onClone(id), [])
    const onEditInternal = useCallback((id) => () => onEdit(id), [])
    const onDeleteInternal = useCallback((id) => () => onDelete(id), [])
    const onPrintInternal = useCallback((id) => (e) => onPrint(id, e.currentTarget), [])

    const calcColumns = [
        ...columns,
        {
            field: 'actions',
            type: 'actions',
            width: (!!onClone && 50) + (!!onEdit && 50) + (!!onDelete && 50) + (!!onPrint && 50) + (actions.length * 50),
            getActions: (params) => [
                ...actions.map(a => <CustomGridActionCellItem className={a.className} label={a.label} onClick={() => a.onClick(params.id)} color={a.color} showInMenu={a.showInMenu} />),
                onClone && <CustomGridActionCellItem className='fa-thin fa-copy' label='Клонировать' onClick={onCloneInternal(params.id)} />,
                onPrint && <CustomGridActionCellItem className='fa-thin fa-print' label='Печать' onClick={onPrintInternal(params.id)} />,
                onEdit && <CustomGridActionCellItem className='fa-thin fa-pen' label='Редактировать' onClick={() => onEdit(params.id)} />,
                onDelete && <CustomGridActionCellItem className='fa-thin fa-trash-xmark' label='Удалить' onClick={() => onDelete(params.id)} color='#ff6251' />,
            ].filter(d => !!d),
        },
    ]

    return (
        <DataGrid
            {...props}
            columns={calcColumns}
            density='compact'
            autoHeight={true}
            slots={{
                loadingOverlay: LinearProgress,
                toolbar: toolbar
            }}
            disableColumnMenu
            showCellVerticalBorder
            localeText={{ toolbarColumns: 'Колонки' }}
        ></DataGrid >
    )
}

export default BaseDataGrid