package br.com.jamesson.aws_s3;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static br.com.jamesson.aws_s3.service.ObjectsServices.listObjects;
import static br.com.jamesson.aws_s3.service.ObjectsServices.uploadObject;

@SpringBootApplication
public class S3WithSpringApplication {

    public static void main(String[] args) {
        //SpringApplication.run(S3WithSpringApplication.class, args);

//		BucketServices.listOfBuckets();
//
//		String bucketName = "second-bucket";
//		Bucket bucket = BucketServices.getBucket(bucketName);
//		System.out.println(bucket);

	/*	String bucketName = "second-bucket-jj";
		Bucket bucket = BucketServices.createBucket(bucketName);

		if(bucket != null){
			System.out.println(bucket);

		}*/

       // uploadObject("dio-class-jj", "index.jpg");

        String bucketName = "dio-class-jj";
        listObjects(bucketName);


    }

}
