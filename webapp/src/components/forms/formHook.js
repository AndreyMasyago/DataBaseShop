import { useState } from 'react';

const useForm = (initialState, callback) => {
  const [formState, setFormState] = useState(initialState);
  
  const handleSubmit = (event) => {
    if (event) {
      event.preventDefault();
    }

    if (callback && !formState.redirect) {
      const result = callback(formState);

      Promise.resolve(result).then(() => {
        setFormState({
          ...formState,
          redirect: true
        });
      })
    } 
  }

  const handleInputChange = (event) => {
    event.persist();
  
    setFormState(formState => ({
      ...formState, 
      [event.target.name]: event.target.value
    }));
  }

  return {
    handleSubmit,
    handleInputChange,
    formState,
    setFormState
  };
}

export default useForm;
