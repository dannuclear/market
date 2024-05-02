import React, { useContext, useEffect, useState } from 'react'
import axios from '../api/axios'
import BaseDataGrid from './BaseDataGrid'
import { LinearProgress } from '@mui/material'
import DefaultToolbar from './DefaultToolbar'
import useDialogState from '../Hooks'
import { MessageContext } from './MessageContext'

const ServerDataGrid = ({
    columns,
    path,
    extraParams,
    onPrint,
    onClone,
    toolbar = DefaultToolbar,
    EditDialog,
    name = 'Запись',
    reload = false,
    getRowId,
    showEdit = true,
    showDelete = true,
    toolbarProps,
    checkboxSelection = false,
    rowSelectionModel,
    onRowSelectionModelChange
}) => {
    const showMessage = useContext(MessageContext)
    const [editDialogState, setEditDialogState] = useDialogState()
    const [item, setItem] = useState(null)
    const [rows, setRows] = useState([])
    const [pageState, setPageState] = useState({
        isLoading: false,
        total: 0,
    })
    const [paginationModel, setPaginationModel] = useState({
        pageSize: 10,
        page: 0
    })
    const [quickFilter, setQuickFilter] = useState()
    const [innerReload, setInnerReload] = useState(false)
    const [inProgress, setInProgress] = useState(false)

    useEffect(() => {
        setPageState((prev) => ({ ...prev, isLoading: true }))
        axios.get(`${path}`, { params: { page: paginationModel.page, size: paginationModel.pageSize, ...quickFilter, ...extraParams } })
            .then(res => {
                setRows(res.data?.content)
                setPageState((prev) => ({ ...prev, isLoading: false, total: res.data?.totalElements ?? 0 }))
            })
            .catch(err => {
                setRows([])
                setPageState((prev) => ({ ...prev, isLoading: false, total: 0 }))
                showMessage(`Ошибка при загрузке ${path}`, 'error')
            })
    }, [paginationModel, innerReload, extraParams, quickFilter, reload])

    useEffect(() => {
        if (item) {
            const dialogTitle = item.id == null ? `Создать ${name}` : `Редактировать ${name}`
            setEditDialogState(state => state.open(dialogTitle))
            //setInProgress(false)
        } else
            setEditDialogState(state => state.close())
    }, [item])


    const onFilterChange = ({ quickFilterValues }) => {
        setQuickFilter(quickFilterValues.length && { search: quickFilterValues[0] })
    }

    const onAdd = () => {
        setItem({})
    }

    const onEdit = (id) => {
        axios.get(`${path}/${id}`)
            .then(res => setItem({ ...res.data, _links: undefined }))
            .catch(err => showMessage(`Ошибка при загрузке ${name}: ` + err.data, 'error'))
    }

    const onDelete = (id) => {
        if (!window.confirm('Удалить запись?'))
            return

        axios.delete(`${path}/${id}`)
            .then(res => { showMessage(`${name} удален`); setInnerReload(s => !s) })
            .catch(err => showMessage(`Ошибка при удалении ${name}: ` + err.data, 'error'))
    }

    const onOkEditDialog = (data) => {
        setInProgress(true)
        const id = data.id ?? 'new'
        axios({
            url: `${path}/${id}`,
            method: id === 'new' ? 'POST' : 'PUT',
            data: {
                ...data,
            }
        }).then(res => {
            showMessage(`${name} успешно сохранен`)
            setItem(null)
            setInnerReload(r => !r)
        }).catch(err => showMessage(`Ошибка при сохранении ${name}: ${err.data}`, 'error'))
            .finally(() => { const intId = setInterval(() => { setInProgress(false); clearInterval(intId) }, 1000) })
    }

    return (
        <>
            <BaseDataGrid
                toolbar={toolbar}
                columns={columns}
                rows={rows}
                loading={pageState.isLoading}
                pageSizeOptions={[10, 20, 30, 50, 70, 100]}
                paginationMode='server'
                filterMode='server'
                onFilterModelChange={onFilterChange}
                paginationModel={paginationModel}
                onPaginationModelChange={setPaginationModel}
                rowCount={pageState.total}
                onEdit={showEdit && onEdit}
                onDelete={showDelete && onDelete}
                onPrint={onPrint}
                onClone={onClone}
                
                disableRowSelectionOnClick
                checkboxSelection={checkboxSelection}
                rowSelectionModel={rowSelectionModel}
                onRowSelectionModelChange={onRowSelectionModelChange}
                keepNonExistentRowsSelected 
                
                slotProps={{
                    toolbar: {
                        onAdd: onAdd,
                        ...toolbarProps
                    }
                }}
                getRowId={getRowId}
            />

            {EditDialog && <EditDialog
                open={editDialogState.isOpen}
                item={item}
                title={editDialogState.title}
                onCancel={() => setItem(null)}
                onOk={onOkEditDialog}
                inProgress={inProgress}
            />}
        </>
    )
}

export default ServerDataGrid