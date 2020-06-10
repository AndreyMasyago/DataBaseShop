import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import { createProvider, getProvider, updateProvider } from '../../../api/provider';

import useForm from '../formHook';

import './styles.css';

function ProvidersForm() {
  const match = useRouteMatch();

  let update = false;
  if (match && match.params.id) {
    update = true;
  }

  const callback = (data) => {
    const dataToSave = {
      ...data
    };

    if (update) {
      return updateProvider(dataToSave)
    } else {
      return createProvider(dataToSave);
    }
  }
  
  const { formState, handleSubmit, handleInputChange, setFormState } = useForm({
    providerName: "",
    category: ""
   }, callback);

  useEffect(() => {
    if (!update) {
      return;
    }

    getProvider(match.params.id).then((data) => {
      setFormState({
        ...data
      })
    });
  }, [match.params.id, setFormState, update]);

  const history = useHistory();

  if (formState.redirect) {
    history.push('/provider/');
  }

  return (
    <div className="ProvidersForm">
      <Form onSubmit={handleSubmit}>
      
      <Form.Label>Поставщики</Form.Label>
        <Form.Group>
          <Form.Label>Название поставщика</Form.Label>
          
          <Form.Control 
            type="text" 
            placeholder="Введите название поставщика" 
            name="providerName" 
            value={formState.providerName} 
            onChange={handleInputChange} 
            required 
          />
        </Form.Group>

        <Form.Group as={Form.Col}>
          <Form.Label>Категория поставщика</Form.Label>
          
          <Form.Control 
            as="select" 
            name="category" 
            value={formState.category} 
            onChange={handleInputChange} 
            required
          >
            <option value="" disabled>Выберите категорию</option>
            <option value="authorized dealer">Фирма/Дилер</option>
            <option value="small manufacture">Небольшое производство</option>
            <option value="store">Магазин</option>
          </Form.Control>
        </Form.Group>
        
        <Button variant="outline-success" type="submit">        
          {update ? 'Сохранить изменения' : 'Добавить поставщика'}
        </Button>
      </Form>
    </div>
  );
}

export default ProvidersForm;
