import styled from "styled-components"
import {useState} from "react"
import axios from "axios"

export default function Generate(){
    const [name, setName] = useState("")
    const [validate,setValidate]=useState("")
    const [requesting,setRequesting] = useState(false)
    const [search, setSearch] = useState(false)

    const[inDay, setInDay] = useState("")
    const[inMonth, setinMonth] = useState("")
    const[finalDay, setFinalDay] = useState("")
    const[finalMonth, setFinalMonth] = useState("")
    const[jsonOb, setJsonOb] = useState([])

    function generate(){
        if(name===""||validate===""){
            alert("Preencha todos os campos")
            return
        }
        if(!Number.isInteger(parseFloat(validate))){
            alert("Preencha a validade apenas com número e inteiro!")
            return
        }
        setRequesting(true)
        const body={name,validate}
        const request = axios.post("http://localhost:8080/Project-1.0-SNAPSHOT/create-certificate", body)
        request.then(r=>{
            setTimeout(() => {
                setRequesting(false)
                setName("")
                setValidate("")
                alert("Certificado gerado!")
            }, 2000)
        })
        request.catch()
    }
    function searchCertificated(){
        
        var inDate = new Date(2021, parseInt(inMonth)-1, parseInt(inDay))
        var finalDate = new Date(2021, parseInt(finalMonth)-1, parseInt(finalDay))
        finalDate.setHours(23,59,59)

        var inDateTime = inDate.getTime().toString()
        var finalDateTime = finalDate.getTime().toString()
            
        setRequesting(true)
        const body={name, inDateTime, finalDateTime}
        const request = axios.post("http://localhost:8080/Project-1.0-SNAPSHOT/search-data", body)
        request.then(r=>{
            setTimeout(() => {
                setRequesting(false)
                setName("")
                setInDay("")
                setinMonth("")
                setFinalDay("")
                setFinalMonth("")
            }, 1000)
            setJsonOb(r.data)
            
        })
        request.catch()
    }

    function deleteCertificate(name, serial){
        name = name.replace("CN=", "")
        const body={name, serial}
        const request = axios.post("http://localhost:8080/Project-1.0-SNAPSHOT/delete-data", body)
        request.then(r=>{
            setTimeout(() => {
                setRequesting(false)
            }, 1000)  
            setName(name)
            searchCertificated()         
        })
        request.catch()
    }
    
    return (
        <>
        <Log>
            <Input type="text" disabled={requesting} placeholder="Nome" value={name} onChange={e=>setName(e.target.value)}></Input>
            <Input type="text" hidden={search} disabled={requesting} placeholder="Validade em dias a partir de hoje" value={validate} onChange={e=>setValidate(e.target.value)}></Input>

            <InputDate type="text" hidden={!search} disabled={requesting} placeholder="Dia de início" value={inDay} onChange={e=>setInDay(e.target.value)}></InputDate>
            <InputDate type="text" hidden={!search} disabled={requesting} placeholder="Mês de início" value={inMonth} onChange={e=>setinMonth(e.target.value)}></InputDate>
            <InputDate type="text" hidden={!search} disabled={requesting} placeholder="Dia de fim" value={finalDay} onChange={e=>setFinalDay(e.target.value)}></InputDate>
            <InputDate type="text" hidden={!search} disabled={requesting} placeholder="Mês de fim" value={finalMonth} onChange={e=>setFinalMonth(e.target.value)}></InputDate>
            
            {search?  
            <Button type="submit" enabled={search} onClick={searchCertificated}>Procurar certificado</Button>:          
            requesting?
                <div>Gerando...</div>:
                <Button type="submit" enabled={search} onClick={generate}>Gerar certificado</Button>}
            {search? <p onClick={()=>setSearch(!search)}>Clique aqui para gerar um certificado</p> : 
                <p onClick={()=>setSearch(!search)}>Clique aqui para procurar um certificado</p>}

                <p hidden={!search}>Clique nas informações abaixo para deletar o certificado clicado!</p>
            
            
                <Ul>{jsonOb.map(objeto=>(
                <>
                <ul onClick={()=>deleteCertificate(objeto.name, objeto.serial)}>
                  <li>Nome: {objeto.name}</li>
                  <li>Número de série: {objeto.serial}</li>
                  <li>Validade: {objeto.validate}</li>
                  <li>Chave pública: {objeto.publicKey}</li>
                </ul>
                </>))}
                  </Ul>
            

        </Log>
        
        </>
    )
}
export const Ul = styled.ul`
    width: 215px;
    background: #FFFFFF;
    border-radius: 6px;
    border: black;
    border-style: solid;
    outline-color:#1877F2;
    font-style: normal;
    font-weight: bold;
    font-size: 10px;
    line-height: 40px;
    word-wrap: break-word;
    color: black;
    padding: 4px;
    ul{
        border-radius: 6px;
        border: black;
        border-style: solid;
        cursor: pointer;
        padding: 4px;
    }
`;

export const InputDate = styled.input`
    width: 215px;
    height: 65px;
    background: #FFFFFF;
    border-radius: 6px;
    border:none;
    outline-color:#1877F2;
    padding-left: 17px;
    font-style: normal;
    font-weight: bold;
    font-size: 27px;
    line-height: 40px;
    color: black;
    ::placeholder{
        color: #9F9F9F;
    }
`;

export const Enabled = styled.div`
    width:40px;
    height:40px;
    background-color: ${props=>props.enabled? "red" :"green"};
    position: absolute;
    right:49%;
    bottom:175px;
    border-radius: 50%;
    cursor:pointer;
`
export const Log = styled.section`
    position: absolute;
    display:flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap:13px;
    width:100%;
    //height: 100%;
    font-family: Arial, Helvetica, sans-serif;
    background-color: #333333;
    p{
        font-style: normal;
        font-weight: normal;
        font-size: 20px;
        line-height: 24px;
        color:white;
        text-decoration: underline;
        cursor:pointer;
    }
    div{
        display:flex;
        align-items: center;
        justify-content: center;
        width: 448px;
        height: 67px;
        background: green;
        border-radius: 6px;
        border:none;
        font-style: normal;
        font-weight: bold;
        font-size: 27px;
        line-height: 40px;
        color: #FFFFFF;
        opacity:0.6;
        cursor: pointer;
    }
`;

export const Title = styled.h1`
    font-weight: bold;
    font-size: 106px;
    line-height: 117px;
    letter-spacing: 0.05em;
    color: #FFFFFF;
`;
export const Description = styled.h2`
    font-weight: bold;
    font-size: 43px;
    line-height: 64px;
    color: #FFFFFF;
`;

export const Input = styled.input`
    width: 429px;
    height: 65px;
    background: #FFFFFF;
    border-radius: 6px;
    border:none;
    outline-color:#1877F2;
    padding-left: 17px;
    font-style: normal;
    font-weight: bold;
    font-size: 27px;
    line-height: 40px;
    color: black;
    ::placeholder{
        color: #9F9F9F;
    }
`;

export const Button = styled.button`
    width: 448px;
    height: 67px;
    background-color: ${props=>props.enabled? "red" :"green"};
    border-radius: 6px;
    border:none;
    font-style: normal;
    font-weight: bold;
    font-size: 27px;
    line-height: 40px;
    color: #FFFFFF;
    cursor: pointer;
`;


