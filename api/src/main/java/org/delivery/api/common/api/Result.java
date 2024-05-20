package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.ErrorCodeIfs;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

  public static Result Ok(){
      return Result.builder()
              .resultCode(ErrorCode.OK.getErrorCode())
              .resultMessage(ErrorCode.OK.getDescription())
              .resultDescription("성공")
              .build();
  }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription("성공")
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    //이걸 제일 많이 쓴다.
  public static Result ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx, String description ){
      return Result.builder() //특정 에러코드와 메세지를 넘기면 result에 담아 response로 내려갈 수 있게 설정
              .resultCode(errorCodeIfs.getErrorCode())
              .resultMessage(errorCodeIfs.getDescription())
              .resultDescription(description)
              .build();
  }

  public static Result ERROR( ErrorCodeIfs errorCodeIfs, String description){
      return Result.builder()
              .resultCode(errorCodeIfs.getErrorCode())
              .resultMessage(errorCodeIfs.getDescription())
              .resultDescription(description)
              .build();
  }

}
