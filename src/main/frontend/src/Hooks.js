import { useMemo, useState } from "react"

export const defaultDialogState = {
    isOpen: false,
    title: '',
    value: {},
    close: function () {
        return { ...this, isOpen: false }
    },
    open: function (title, value) {
        return {
            ...this, isOpen: true, title: title ?? this.title, value: value ?? this.value
        }
    }
}

const useDialogState = () => {
    return useState(defaultDialogState)
}

export const useInputs = initialState => {
    const mappedInputs = useMemo(() => Object.fromEntries(
        Object.entries(initialState).map(([name, obj]) => {
            const isObject = typeof obj == 'object'
            return [name,
                {
                    name: name,
                    error: isObject && (obj.error ?? false),
                    helperText: isObject && (obj.helperText ?? ''),
                    value: isObject ? obj.value ?? '' : obj,
                    onChange: ({ target: { name, value } }) => {
                        let validateMessage;
                        if (obj.validate)
                            validateMessage = obj.validate(value)
                        setInputs((prev) => ({ ...prev, [name]: { ...prev[name], value: value, helperText: validateMessage, error: Boolean(validateMessage) } }))
                        if (isObject && obj.onChange)
                            obj.onChange(value)
                    }
                }]
        }
        )
    ), [])

    const [inputs, setInputs] = useState(mappedInputs)
    return [inputs, setInputs]
}

export const usePrint = (initialState = { isOpen: false, params: null }) => {
    const [state, setState] = useState(initialState)

    const open = params => {
        setState(prev => ({ ...prev, isOpen: true, params: params }))
    }

    const close = () => {
        setState(prev => ({ ...prev, isOpen: false }))
    }

    return { open, close, isOpen: state.isOpen, params: state.params }
}

export default useDialogState