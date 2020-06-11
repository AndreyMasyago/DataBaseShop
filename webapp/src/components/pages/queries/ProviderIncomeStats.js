import React, { useState, useEffect } from 'react';
import Form from 'react-bootstrap/Form';

import { getProviderIncomeStats } from '../../../api/queries';
import { listProviders } from '../../../api/provider';
import useForm from '../../forms/formHook';


export default function ProviderIncomeStats () {
  const [data, setData] = useState({});

  const [providers, setProviders] = useState([]);
  useEffect(() => { listProviders().then(setProviders); }, []);

  const callback = formState => getProviderIncomeStats(formState).then(data => setData(data));

  const { formState, handleInputChange, handleSubmit } = useForm({
    providerSearch: ''
  }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
    <Form onSubmit={handleSubmit}>
        <Form.Group as={Form.Col}>
          <Form.Label>Поставщик</Form.Label>
          <Form.Control
            as="select" 
            name="providerSearch" 
            value={formState.providerSearch} 
            onChange={handleInputChange}
            required
          >
            <option value="" disabled>Выберите поставщика</option>
            {providers.map(p => (
              <option key={p.providerId} value={p.providerId}>{p.providerName}</option>
            ))}
          </Form.Control>
        </Form.Group>
    </Form>
    
      <h1>Provider Income Stats</h1>
      <div>Share Of Sale: {data.shareOfSale * 100}%</div>
      <div>Share Of Amount: {data.shareOfAmount * 100}%</div>

    </div>
  );
}