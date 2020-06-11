import React, { useState, useEffect } from 'react';

import { getOverHead } from '../../../api/queries';
import useForm from '../../forms/formHook';


export default function OverHead () {
  const [data, setData] = useState([]);

  const callback = formState => getOverHead().then(data => setData(data.results));

  const { formState } = useForm({
    overhead: ''
  }, callback);

  useEffect(() => { callback(formState) }, [formState]);

  return (
    <div>
      <h1>OverHead</h1>
      Тут чиселко: {data}


    </div>
  );
}