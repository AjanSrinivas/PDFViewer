import { Injectable } from '@angular/core';
import { map } from 'rxjs/Operators';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FetchService {

  constructor(private http: HttpClient) { }

  url: string = "http://localhost:9090/ITextPOC/service/fetchDetails/getPosterDetails";

  _postData(firmNm, execSummary){
    let _fd: FormData = new FormData();
    _fd.append('firmNm', firmNm);
    _fd.append('executiveSummary', execSummary);
    return this.http.post(this.url,_fd).pipe(map((data:any)=> data));
  }
}
