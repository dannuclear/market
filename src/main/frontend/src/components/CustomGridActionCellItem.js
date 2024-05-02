import { GridActionsCellItem } from "@mui/x-data-grid"
import { Icon } from "@mui/material";

const CustomGridActionCellItem = ({
    className,
    label,
    onClick = () => console.log('Не задан обработчик'),
    color = 'rgba(0, 0, 0, 0.54)',
    showInMenu = false
}) => {
    return (
        <GridActionsCellItem
            // icon={<FontAwesomeIcon icon={icon} size='lg' style={{ cursor: 'pointer', color: color }} title={label} />}
            icon={<Icon baseClassName={className} sx={{ fontSize: 25, cursor: 'pointer', color: color }} title={label}></Icon>}
            label={label}
            onClick={onClick}
            showInMenu={showInMenu}
        />
    )
}

export default CustomGridActionCellItem

