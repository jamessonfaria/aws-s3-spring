package br.com.jamesson.api_upload;

import br.com.jamesson.aws_s3.service.BucketServices;
import br.com.jamesson.aws_s3.service.ObjectsServices;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/buckets")
public class ApiResource {

    @GetMapping
    public ResponseEntity<?> listOfBuckets(){
        try{
            List<Bucket> buckets = BucketServices.listOfBuckets();
            return new ResponseEntity<>(buckets, HttpStatus.OK);
        }catch (AmazonS3Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{bucketName}")
    public ResponseEntity<?> getBucket(@PathVariable String bucketName){
        try{
            Bucket bucket = BucketServices.getBucket(bucketName);
            if(bucket == null)
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header(HttpHeaders.CONTENT_TYPE)
                        .body("Bucket name doesn't exists");
            return new ResponseEntity<>(bucket, HttpStatus.OK);
        }catch (AmazonS3Exception e) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .header(HttpHeaders.CONTENT_TYPE)
                    .body("Bucket name doesn't exists!\n" +
                            "Message error: " + e.getMessage());
        }
    }

    @GetMapping("/{bucketName}/objects")
    public ResponseEntity<?> getObjects(@PathVariable String bucketName){
        try{
            List<S3ObjectSummary> result = ObjectsServices.listObjects(bucketName);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (AmazonS3Exception e) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .header(HttpHeaders.CONTENT_TYPE)
                    .body("Objects doesn't exists!\n" +
                            "Message error: " + e.getMessage());
        }
    }

    @PutMapping("/{bucketName}/objects/{filePath}")
    public ResponseEntity<?> uploadAnImage(@PathVariable String bucketName,
                                           @PathVariable String filePath){
        try {
            ObjectsServices.uploadObject(bucketName, filePath);
            String messageResponse = "The file with the name " + new File(filePath).getName() +
                    " is storage on the bucket " + bucketName;
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } catch (AmazonS3Exception e){
            return new ResponseEntity<>(e.getStackTrace(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{newBucketName}")
    public ResponseEntity<?> createBucket(@PathVariable String newBucketName){
        try{
            Bucket newBucket = BucketServices.createBucket(newBucketName);
            return new ResponseEntity<>(newBucket, HttpStatus.OK);
        }catch (AmazonS3Exception e){
            return new ResponseEntity<>(e.getStackTrace(), HttpStatus.BAD_REQUEST);
        }
    }


}
