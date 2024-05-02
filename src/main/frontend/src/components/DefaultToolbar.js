import { Box, Button } from "@mui/material"
import { GridToolbarContainer, GridToolbarQuickFilter } from "@mui/x-data-grid"

const DefaultToolbar = ({
    onAdd,
}) => {
    return (
        <GridToolbarContainer>
            <Button variant='outlined' size='small' onClick={onAdd}>Создать</Button>
            <Box sx={{ flex: 1 }} />
            <GridToolbarQuickFilter debounceMs={500} sx={{ width: '20%' }} placeholder='Поиск...' />
        </GridToolbarContainer>
    )
}

export default DefaultToolbar